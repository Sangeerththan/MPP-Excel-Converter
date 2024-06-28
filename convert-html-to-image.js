const puppeteer = require('puppeteer');
const path = require('path');

(async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();

  // Construct the file URL
  const filePath = path.resolve(process.env.RUNNER_TEMP, 'dependency-check-report.html');
  const fileUrl = 'file:///' + filePath;

  await page.goto(fileUrl, { waitUntil: 'networkidle2' });

  // Save the screenshot to the temporary directory
  const outputFilePath = path.resolve(process.env.RUNNER_TEMP, 'dependency-check-report.png');
  await page.screenshot({ path: outputFilePath });

  await browser.close();
})();
