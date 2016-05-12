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
                name: "",
                email: "",
                title: ""
            }));

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
        handleDemoButtonPress: function (oEvent) {
            MessageBox.show(
                "Surprise motherfucker! (Samuel L. Jackson, 2006)",
                {
                    icon: MessageBox.Icon.INFORMATION,
                    title: "Information",
                    actions: [MessageBox.Action.OK]
                }
            );
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
            var file = oEvent.mParameters.files[0];
            if (file != undefined && file.type == "audio/mp3") this.getView().byId("trackFileUploader").setValueState("Success");
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