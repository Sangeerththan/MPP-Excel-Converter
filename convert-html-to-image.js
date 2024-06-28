const puppeteer = require('puppeteer');
const path = require('path');

(async () => {
  try {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();

    // Construct the file URL
    const filePath = path.resolve(process.env.RUNNER_TEMP, 'reports/dependency-check-report.html');
    const fileUrl = 'file:///' + filePath.replace(/\\/g, '/'); // Convert backslashes to forward slashes for Windows compatibility

    console.log('Navigating to:', fileUrl);
    await page.goto(fileUrl, { waitUntil: 'networkidle2' });

    // Save the screenshot to the temporary directory
    const outputFilePath = path.resolve(process.env.RUNNER_TEMP, 'reports/dependency-check-report.png');
    await page.screenshot({ path: outputFilePath });

    console.log('Screenshot saved successfully:', outputFilePath);

    await browser.close();
  } catch (error) {
    console.error('Error capturing screenshot:', error);
    process.exit(1); // Exit with non-zero code on error to fail the GitHub Action
  }
})();
