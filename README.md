# Sensors Library
[![](https://jitpack.io/v/tomermusafi/Y4S1T1.svg)](https://jitpack.io/#tomermusafi/Y4S1T1)

## Prerequisites
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

## Dependency
Add this to your module's build.gradle file (make sure the version matches the JitPack badge above):
 ```java
  dependencies {
	        implementation 'com.github.tomermusafi:Y4S1T1:01.00.01'
	}
```

## Usage
### create MySensor
```java
mySensors = new MySensor(callBack_MySensors, this);
```
### Implement CallBack
```java
    MySensor.CallBack_MySensors callBack_MySensors = new MySensor.CallBack_MySensors() {
        @Override
        public void getMagnetDirection(float magnet) {
            //your code
        }

        @Override
        public void getProximity(float proximity) {
            //your code
        }

        @Override
        public void getLight(float light) {
           //your code
        }
    };

```
### Implement in onResume
```java
@Override
    protected void onResume() {
        super.onResume();
        mySensors.registerSensors();
    }
```
### Implement in onResume
```java
    @Override
    protected void onPause() {
        super.onPause();
        mySensors.unregisterSensors();
    }
```

### ScreenShoot
![alt text](https://drive.google.com/uc?export=view&id=1TkjEVocZaA9J-RtG_5S_oPmbj5ERTC5v)

<img src="https://drive.google.com/uc?export=view&id=1TkjEVocZaA9J-RtG_5S_oPmbj5ERTC5v" alt="drawing" width="200"/>
