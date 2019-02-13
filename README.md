# CM_sign
Uses the CM Sign API to automate the signing of digital documents by clients.

CM Sign API docs:
https://docs.cmtelecom.com/nl/api/cm-sign/1.0/index/

Dependencies:
* log4jv2 - logging errors and normal flow
* jjwt - json web token authentication with API
* Apache httpcomponents - HTTP Communication with API
* Jackson Core - Serializing to and from JSON

Todo:
1. Move to production API
2. Catch all exception paths
3. Integrate project with Spring backend
