# UnifiedHRS
Healthcare is one of the largest industries in the world. By 2025, it is anticipated that the global healthcare market will reach $11.9 trillion. Since the COVID-19 pandemic, technology has played a significant role in supporting the healthcare infrastructure worldwide. As a result, the use of telehealth services has grown significantly. 
Also, {electronic health records (EHRs) have become increasingly popular in recent years, with 98\% of hospitals in the United States using some form of this technology as of 2021. However, the existing healthcare infrastructure has several problems.
* Data privacy and security: It is common for hackers to target healthcare data due to its sensitive and confidential nature. How can we make it more secure?
* Interoperability: The availability of healthcare data is often limited because it is stored in silos, making it difficult for different providers to share and access patient information. How can this issue be addressed?
* Data integrity and accuracy: The accuracy and reliability of healthcare data are often compromised by errors such as duplicate records or incorrect diagnoses. In what ways can we ensure the accuracy and reliability of healthcare data?

Blockchain has the potential to address these challenges. 
Its cryptographic protocols and distributed ledger technology provide inherent security features that can help protect healthcare data from unauthorized access and tampering, thus addressing privacy and security concerns. Secondly, blockchain technology can serve as a standardized and secure platform for sharing patient data across providers without compromising privacy and enhancing interoperability. Lastly, due to blockchain's immutable nature, it can be used to establish a single source of truth for healthcare data, thus ensuring its accuracy and reliability. Therefore, we propose to develop a Unified Health Record system for patients using a permission-based Blockchain infrastructure, enabling a patient's healthcare data to be managed securely, transparently, and decentrally while protecting user privacy at the same time.


## Technology Stack
User Application - Kotlin and Android\
Web Dashboard - Javascript and Vue\
Blockchain - Solidity and Ethereum\
Notification and Permission Management - Google Firebase\
Version Control - Git

![](https://img.icons8.com/color/48/000000/kotlin.png) ![icons8-javascript-48](https://user-images.githubusercontent.com/15179100/233170104-21245dcd-0e03-42c2-ad0f-f4aa4d2abc4f.png)  ![](https://img.icons8.com/color/48/000000/android-studio--v3.png) ![icons8-vue-js-48](https://user-images.githubusercontent.com/15179100/233169832-500efed7-440f-4b75-b7d2-94a4e546b2da.png) ![icons8-firebase-48](https://user-images.githubusercontent.com/15179100/226479783-721c46be-5e66-471c-baf5-1a9f9eb51370.png) ![icons8-solidity-48](https://user-images.githubusercontent.com/15179100/233171359-2a838e28-5ac2-4fd7-bce4-c0e3ec183508.png) ![icons8-ethereum-48](https://user-images.githubusercontent.com/15179100/226479782-b22889ca-398c-4012-948b-69b05bb6fcce.png) ![](https://img.icons8.com/color/48/000000/git.png)


## Component Overview
The Unified Health Record System (HRS), consists of three major components:
1. The Android based Mobile Application that can be used by consumers/ patients in order to view their personal information, past medical records and account access.
2. Vue.js based Web-Dashboard used by Health/ Medical professionals such as, hospitals, medical-centres to view patient records, data and request access to medical records
3. Etheruem based Blockchain infrasturucture which facilitates updating and viewing patient details.

The Android based mobile application uses Infura SDK to interact with the Ethereum Blockchain Network via the Goerli Test Network.
The User-Permission access mechanism is developed using Google Firebase Notification Channel.\
The overall architecture-diagram of the Unified EHS application is shown below:

![arch_diagram](https://user-images.githubusercontent.com/15179100/233177579-9fe6ecd1-19f8-4070-b8ac-caea359cdb94.png)


## Running the Code
Based on the Technology-Stack and functionalities, the repository is divided into three branches. Each branch needs to be compiled and executed separately.

1. android - User application based on Android
2. ui - Web Dashboard based on Vue
3. blockchain - Backend based on Ethereum

### Part-1 (Building the Android Mobile Application)
1. Download the code from the current repository either as a zip file or use Git or checkout with SVN using the below web URL. Open the repository via Android Studio.
```
https://github.com/ranitraj/MM802-UnifiedHRS.git
```
2. Checkout to the branch 'android' using the below command via the terminal.
```
git checkout -b origin android
```
3. Clean and Rebuild the project using Android Studio. This can be performed through the UI or via the terminal using the following commands.
```
./gradlew clean
./gradlew build
```
4. Once the project is compiled successfully, the same can be installed on a real-device or an emulator. To flash an APK into a device, the below command can be used.\
NOTE: ADB must be installed prior to executing this command.
```
adb install path-to-apk-file.apk
```

### Part-2 (Building the Vue Web-Dashboard)
1. Run `yarn install`\
This will install both run-time project dependencies and developer tools listed
in [package.json](../package.json) file. We are moving all dependencies to npm, so there will be no bower dependencies soon.

2. Run `yarn build`\
This command will build the app from the source files (`/src`) into the output
`/dist` folder. Then open `dist/index.html` in your browser.

Now you can open your web app in a browser, on mobile devices and start
hacking. The page must be served from a web server, e.g. apache, nginx, WebStorm built-in web server, etc., otherwise some features may not work properly.

3. Run `yarn serve`\
This command will watch for changes in `/src` and recompile vue templates & scss styles on the fly.

## Screenshots

### Android User Application
<img src="https://user-images.githubusercontent.com/15179100/233164381-10953dfb-4be4-4014-986d-8d50c61d9f49.jpg" width="250" height="450">
<img src="https://user-images.githubusercontent.com/15179100/233164366-55450b55-379f-4bbb-a064-3f7d8b02414b.jpg" width="250" height="450">
<img src="https://user-images.githubusercontent.com/15179100/233164368-de451d7d-83c3-42ef-992f-fcd0c936c9e4.jpg" width="250" height="450">

### Vue Access/Data Management Dashboard
<img src="https://user-images.githubusercontent.com/15179100/233178495-d1e6c633-0592-4727-bf41-9eee3cb5f7aa.png" width="800" height="450">
