@echo off
echo # 🧪 Selenium Automation Framework (MCB) > README.md
echo. >> README.md
echo This is a Selenium automation framework built using Java, Maven, Selenium WebDriver, and TestNG. >> README.md
echo It also integrates OCR using Tesseract for Captcha decoding and custom reporting using ExtentReports. >> README.md
echo. >> README.md

echo ## 📁 Project Structure >> README.md
echo. >> README.md
echo ^| Module ^| Description ^| >> README.md
echo ^|--------^|-------------^| >> README.md
echo ^| `com.jk.selenium.Pages` ^| Page Object Model classes for Login, Dashboard, OTP ^| >> README.md
echo ^| `com.jk.selenium.Tests` ^| TestNG test classes including LoginTest, MCBLoginTest ^| >> README.md
echo ^| `com.jk.selenium.Util` ^| Utilities like ConfigReader, CaptchaExtractor ^| >> README.md
echo ^| `com.jk.selenium.mcb.ocr` ^| OCR module using Tesseract for captcha recognition ^| >> README.md
echo ^| `com.jk.selenium.reports` ^| ReportManager for ExtentReport integration ^| >> README.md
echo. >> README.md

echo ## 🚀 How to Run >> README.md
echo. >> README.md
echo 1. Clone this repo using `git clone <repo-url>` >> README.md
echo 2. Open the project in IntelliJ IDEA >> README.md
echo 3. Ensure Maven and JDK are properly set up >> README.md
echo 4. Update `config.properties` and `testng.xml` as needed >> README.md
echo 5. Run test classes via TestNG runner or using `mvn test` >> README.md
echo. >> README.md

echo ## 🔍 Key Features >> README.md
echo. >> README.md
echo - Page Object Model architecture >> README.md
echo - Configurable TestNG XML >> README.md
echo - Captcha decoding using OCR (Tesseract) >> README.md
echo - Custom test reporting via ExtentReports >> README.md
echo - Environment-specific `config.properties` >> README.md
echo. >> README.md

echo ## 📦 Dependencies >> README.md
echo. >> README.md
echo - Java 11+ >> README.md
echo - Maven >> README.md
echo - Selenium WebDriver >> README.md
echo - TestNG >> README.md
echo - Tesseract OCR >> README.md
echo - ExtentReports >> README.md
echo. >> README.md

echo ## 🧩 Important Files >> README.md
echo. >> README.md
echo - `pom.xml` – Project dependencies >> README.md
echo - `testng.xml` – Test suite configuration >> README.md
echo - `config.properties` – Test configurations like URL, credentials >> README.md
echo. >> README.md

echo ## 📸 Captcha OCR Integration >> README.md
echo. >> README.md
echo The framework uses Tesseract for decoding captchas in login flows. Captured images are stored as `captcha.png` and passed through `CaptchaOCR.java`. >> README.md
echo. >> README.md

echo ## 👨‍💻 Author >> README.md
echo. >> README.md
echo - Developed by: krish (or your name) >> README.md
echo - GitHub: https://github.com/Krushnraj369/SeleniumFramework>> README.md
echo. >> README.md

echo ✅ README.md has been generated successfully!
- GitHub: https://github.com/Krushnraj369/SeleniumFramework 
