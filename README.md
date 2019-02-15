# CM_sign
Uses the CM Sign API to automate the signing of digital documents by clients.

CM Sign API docs:
https://docs.cmtelecom.com/nl/api/cm-sign/1.0/index/

## How to use  
The class CmSignApi serves as an abstraction of the REST API offered by CM.  
Simply create an object and call the sendInvitationsForFiles method. (see example below)

```java
public static void main(String[] args) throws FileNotFoundException {
    Invitee invitee = new Invitee("Maxim", "maxim.coppieters@student.pxl.be");

    PdfFile pdfFile = new PdfFile(Paths.get("path", "to", "pdf", "contract"));

    CmSignApi cmSignApi = new CmSignApi();
    Logger logger = LogManager.getLogger(Main.class.getName());
    try {
        cmSignApi.sendInvitationForPdfFilePath(pdfFile, invitee);
    } catch(CmSignException e) {
        logger.error("Couldn't send invitation email for document "
                + pdfFile.getFileName() + ". Reason: " + e.getMessage());
    }
}
```

In case you don't want to save the generated PDF file locally (as CM will save it for you),
you can also create send the PdfFile binary as an InputStream object.

## How it works  
Under the hood the API will do the following things:
1. Take the path or binary input stream of a PDF file, and a number of Invitees (POJO user object with name and email)
2. Upload the file, creating a document on CM's service
3. Take the ID of the created document and assign invitees to them and use this data to create a dossier on CM.
4. Take the generated ID of the generated dossier with its associated invitees and send an invitation request to CM.
5. CM will send an email with a link to each invitee's email address. When the person clicks the link, de invitee sees
the content of the PDF file. The location at which the invitee is required to sign, will be highlighted on the document.
When the person clicks the highlight, he/she can choose a signature style. The actual signature will be their name.
6. When every invitee has signed the document, the owner will get an email with an overview of all signatures (and the dates on which they were made)

## Dependencies
* log4jv2 - logging errors and normal flow
* jjwt - json web token authentication with API
* Apache httpcomponents - HTTP Communication with API
* Jackson Core - Serializing to and from JSON

## Todo
1. Move to production API
2. Catch all exception paths
3. Integrate project with Spring backend
