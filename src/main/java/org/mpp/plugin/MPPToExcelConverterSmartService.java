package org.mpp.plugin;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.knowledge.Document;
import com.appiancorp.suiteapi.knowledge.DocumentDataType;
import com.appiancorp.suiteapi.knowledge.FolderDataType;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.InputStream;

@PaletteInfo(paletteCategory = "Appian Smart Services", palette = "Document Management")
public class MPPToExcelConverterSmartService extends AppianSmartService {

    private Long mppDocument;
    private Long saveInFolder;

    private boolean errorOccurred;
    private String errorMessage;

    private final SmartServiceContext smartServiceCtx;
    private final ContentService contentService;

    private static final Logger LOG = Logger.getLogger(MPPToExcelConverterSmartService.class);

    @Input(required = Required.ALWAYS)
    @Name("mppDocument")
    @DocumentDataType
    public void setMppDocument(Long val) {
        this.mppDocument = val;
    }

    @Input(required = Required.ALWAYS)
    @Name("saveInFolder")
    @FolderDataType
    public void setSaveInFolder(Long val) {
        this.saveInFolder = val;
    }

    public MPPToExcelConverterSmartService(SmartServiceContext smartServiceCtx, ContentService cs) {
        super();
        this.smartServiceCtx = smartServiceCtx;
        this.contentService = cs;
    }


    @Override
    public void run() throws SmartServiceException {
        try {
            Document inputDocument = contentService.download(mppDocument, ContentConstants.VERSION_CURRENT, false)[0];
            InputStream inputStream = inputDocument.getInputStream();

            ProjectFile projectFile = new MPPReader().read(inputStream);
            Workbook workbook = ExcelWorkbookFactory.createWorkbookFromProjectFile(projectFile);



            // Construct the file path
            String excelFilePath = getSaveInFolder() + "/" + inputDocument.getName().replace(".mpp", ".xlsx");

            // Create FileOutputStream with the file path
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
        } catch (Exception e) {
            errorOccurred = true;
            errorMessage = e.getMessage();
            LOG.error("Error converting MPP to Excel", e);
            throw new SmartServiceException.Builder(getClass(), e).build();
        }
    }

    public Long getSaveInFolder() {
        return saveInFolder;
    }
}
