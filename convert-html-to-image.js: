const puppeteer = require('puppeteer');

(async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto('file:///' + process.cwd() + '/reports/dependency-check-report.html', {waitUntil: 'networkidle2'});
  await page.screenshot({path: 'reports/dependency-check-report.png', fullPage: true});
  await browser.close();
})();
