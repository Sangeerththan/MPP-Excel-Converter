const puppeteer = require('puppeteer');
const path = require('path');

(async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  
  // Construct the file URL
  const filePath = path.resolve(process.env.GITHUB_WORKSPACE, 'reports/dependency-check-report.html');
  const fileUrl = 'file:///' + filePath;
  
  await page.goto(fileUrl, { waitUntil: 'networkidle2' });

  await page.screenshot({ path: 'reports/dependency-check-report.png' });

  await browser.close();
})();
