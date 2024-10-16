# Sample Android App Using MockWebServer With Maestro Tests

This repository contains a sample app that uses MockWEbServer for android as a mock API server and runs UI tests with Maestro.
  
**Maestro Test Run On Android Physical Device**

## Prerequisites

- Ensure that [Maestro](https://maestro.mobile.dev/getting-started) is installed on your machine.

## Run Tests

### Android

- connect physical android device or emulator
  - make sure device shows up with `adb devices`
- `./scripts/android_ui_test.sh`

## Note

For Android devices to communicate with an HTTP server, the app must allow cleartext traffic in its network_security_config.  
Use the following configuration

```xml
<!-- cleartextTrafficPermitted is required to communicate over http -->
<!-- note the lack of system certs-->
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="user" />
        </trust-anchors>
    </base-config>
</network-security-config>
```
