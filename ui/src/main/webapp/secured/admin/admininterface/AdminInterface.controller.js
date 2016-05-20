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

    var AdminInterfaceController = Controller.extend("admininterface.AdminInterface", {
        onInit: function () {
            //Set model
            this.getView().setModel(new JSONModel({
                name: "",
                email: "",
                title: ""
            }));

            $.ajax({
                url: "/rest/demo/findAll",
                type: "GET",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    console.log(data);
                    MessageBox.success(
                        data[0],
                        {
                            title: "Success!",
                            actions: [MessageBox.Action.OK]
                        }
                    );

                },
                error: function (xhr, status) {
                    console.log(xhr);
                    console.log(status);
                    MessageBox.error(
                        xhr.responseText,
                        {
                            title: "Error!",
                            actions: [MessageBox.Action.OK]
                        }
                    );
                    var messages = JSON.parse(xhr.responseText);
                    console.log(messages);
                },
                complete: function (xhr, status) {

                }
            });
        },
        onExit: function () {
            //Pls do not leave
        }
    });

    return AdminInterfaceController;
}, /* bExport= */ true);