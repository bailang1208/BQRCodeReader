# Bar & QR code reader

## Introduction

Android library projects that can scan easy to use Barcode and QRcode based on ZXing.

## Screenshots
<img src="/screenshots/image_01.jpeg" width="266">
<img src="/screenshots/image_02.jpeg" width="266">
<img src="/screenshots/image_03.jpeg" width="266">

## Installation

Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.bailang1208:BQRCodeReader:0.0.1'
}
```