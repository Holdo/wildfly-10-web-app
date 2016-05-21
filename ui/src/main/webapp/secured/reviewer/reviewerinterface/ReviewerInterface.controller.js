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

    var ReviewerInterfaceController = Controller.extend("reviewerinterface.ReviewerInterface", {
        onInit: function () {
            this.refreshTable();
        },
        onExit: function () {
            //Pls do not leave
        },
        handleColumnPress: function (oEvent) {
            console.log(oEvent.getSource());
            console.log(oEvent.getParameters());
            MessageToast.show("Press event fired!");
        },
        refreshTable: function () {
            var oView = this.getView();
            $.ajax({
                url: "/rest/demo/findAll",
                type: "GET",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    oView.setModel(new JSONModel(data));
                    console.log("Received JSON:" + oView.getModel().getJSON());

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
        }
    });

    return ReviewerInterfaceController;
}, /* bExport= */ true);