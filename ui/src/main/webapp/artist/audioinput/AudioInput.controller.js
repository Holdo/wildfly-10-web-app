sap.ui.define([
    'jquery.sap.global',
    'sap/ui/core/mvc/Controller',
    'sap/ui/core/Fragment',
    'sap/ui/model/Filter',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageToast",
    "sap/m/MessageBox",
    'sap/ui/model/SimpleType',
    'sap/ui/model/ValidateException'
], function (jQuery, Controller, Fragment, Filter, JSONModel, MessageToast, MessageBox, SimpleType, ValidateException) {
    "use strict";

    var AudioInputController = Controller.extend("audioinput.AudioInput", {
        onInit: function () {
            //Set model
            this.getView().setModel(new JSONModel({
                artist: "",
                email: "",
                title: "",
                fileName: "",
                file: ""
            }));

            this.file = null;

            // attach handlers for validation errors
            sap.ui.getCore().attachValidationError(function (evt) {
                var control = evt.getParameter("element");
                if (control && control.setValueState) {
                    control.setValueState("Error");
                }
            });
            sap.ui.getCore().attachValidationSuccess(function (evt) {
                var control = evt.getParameter("element");
                if (control && control.setValueState) {
                    control.setValueState("Success");
                }
            });
        },
        onExit: function () {
            //Pls do not leave
        },
        handleFileTypeMissmatch: function (oEvent) {
            var aFileTypes = oEvent.getSource().getFileType();
            jQuery.each(aFileTypes, function (key, value) {
                aFileTypes[key] = "*." + value
            });
            this.getView().byId("trackFileUploader").setValueState("Error");
            var sSupportedFileTypes = aFileTypes.join(", ");
            MessageToast.show("The file type *." + oEvent.getParameter("fileType") +
                " is not supported. Choose one of the following types: " + sSupportedFileTypes);
        },
        handleFileUploaderChange: function (oEvent) {
            //console.log(jsonModel);
            var file = oEvent.mParameters.files[0];
            if (file != undefined && file.type == "audio/mp3") this.getView().byId("trackFileUploader").setValueState("Success");
            this.file = file;
        },
        handleDemoButtonPress: function (oEvent) {
            if (
                this.getView().byId("artistInput").getValueState() != "Success" &&
                this.getView().byId("emailInput").getValueState() != "Success" &&
                this.getView().byId("titleInput").getValueState() != "Success" &&
                this.getView().byId("trackFileUploader").getValueState() != "Success") {
                MessageBox.error(
                    "Please fill all the fields correctly.",
                    {
                        title: "Information",
                        actions: [MessageBox.Action.OK]
                    }
                );
                return;
            }

            var file = this.file;
            var jsonModel = this.getView().getModel();
            var jsonString;
            binaryFileToString(file, function (fileName, fileString) {
                jsonModel.setProperty("/fileName", fileName);
                jsonString = jsonModel.getJSON();
                console.log(jsonString);
                jsonString = jsonString.substring(0, jsonString.length -2);
                console.log(jsonString);

                $.ajax({
                    url: "/rest/demo/upload",
                    type: "PUT",
                    data: jsonString + fileString + "\"}",
                    dataType: "json",
                    contentType: "application/json; charset=\"utf-8\"",
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        MessageBox.success(
                            "Track has been uploaded!",
                            {
                                title: "Information",
                                actions: [MessageBox.Action.OK]
                            }
                        );
                    },
                    error: function (xhr, status) {
                        console.log(status);
                        console.log(xhr.responseText);
                        MessageBox.error(
                            "ERROR! Response logged in the web browser console.",
                            {
                                title: "Information",
                                actions: [MessageBox.Action.OK]
                            }
                        );
                    },
                    complete: function (xhr, status) {

                    }
                });
            });
        },
        /**
         * This is a custom model type for validating email
         */
        typeEMail: SimpleType.extend("email", {
            formatValue: function (oValue) {
                return oValue;
            },
            parseValue: function (oValue) {
                //parsing step takes place before validating step, value can be altered
                return oValue;
            },
            validateValue: function (oValue) {
                // The following Regex is NOT a completely correct one and only used for demonstration purposes.
                // RFC 5322 cannot even be checked by a Regex and the Regex for RFC 822 is very long and complex.
                var mailregex = /^\w+[\w-+\.]*\@\w+([-\.]\w+)*\.[a-zA-Z]{2,}$/;
                if (!oValue.match(mailregex)) {
                    throw new ValidateException("'" + oValue + "' is not a valid email address");
                }
            }
        })
    });

    return AudioInputController;
}, /* bExport= */ true);
function binaryFileToString(file, callback) {
    console.log("binaryFileToString");
    if (file) {
        var reader = new FileReader();

        // On load set file contents to text view
        reader.onload = function(oEvent) {
            callback(file.name, arrayBufferToBase64(oEvent.target.result));
        };

        // Read in the file as text
        reader.readAsArrayBuffer(file);
    }
}
function arrayBufferToBase64(arrayBuffer) {
    console.log("arrayBufferToBase64_START");
    var base64    = '';
    var encodings = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';

    var bytes         = new Uint8Array(arrayBuffer);
    var byteLength    = bytes.byteLength;
    var byteRemainder = byteLength % 3;
    var mainLength    = byteLength - byteRemainder;

    var a, b, c, d;
    var chunk;

    // Main loop deals with bytes in chunks of 3
    for (var i = 0; i < mainLength; i = i + 3) {
        // Combine the three bytes into a single integer
        chunk = (bytes[i] << 16) | (bytes[i + 1] << 8) | bytes[i + 2];

        // Use bitmasks to extract 6-bit segments from the triplet
        a = (chunk & 16515072) >> 18; // 16515072 = (2^6 - 1) << 18
        b = (chunk & 258048)   >> 12; // 258048   = (2^6 - 1) << 12
        c = (chunk & 4032)     >>  6; // 4032     = (2^6 - 1) << 6
        d = chunk & 63;               // 63       = 2^6 - 1

        // Convert the raw binary segments to the appropriate ASCII encoding
        base64 += encodings[a] + encodings[b] + encodings[c] + encodings[d]
    }

    // Deal with the remaining bytes and padding
    if (byteRemainder == 1) {
        chunk = bytes[mainLength];

        a = (chunk & 252) >> 2; // 252 = (2^6 - 1) << 2

        // Set the 4 least significant bits to zero
        b = (chunk & 3)   << 4; // 3   = 2^2 - 1

        base64 += encodings[a] + encodings[b] + '=='
    } else if (byteRemainder == 2) {
        chunk = (bytes[mainLength] << 8) | bytes[mainLength + 1];

        a = (chunk & 64512) >> 10; // 64512 = (2^6 - 1) << 10
        b = (chunk & 1008)  >>  4; // 1008  = (2^6 - 1) << 4

        // Set the 2 least significant bits to zero
        c = (chunk & 15)    <<  2; // 15    = 2^4 - 1

        base64 += encodings[a] + encodings[b] + encodings[c] + '='
    }
    console.log("arrayBufferToBase64_DONE");
    return base64
}