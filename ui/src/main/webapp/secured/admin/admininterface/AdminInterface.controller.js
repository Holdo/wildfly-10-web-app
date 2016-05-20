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
            this.refreshTable();
        },
        onExit: function () {
            //Pls do not leave
        },
        handleTableRowDelete: function (oEvent) {
            var sTrackTitle = oEvent.getParameters().listItem.getCells()[0].getText();
            var that = this;
            $.ajax({
                url: "/rest/demo/" + sTrackTitle,
                type: "DELETE",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    MessageToast.show("Track deleted!");
                    that.refreshTable();
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
        handleColumnPress: function (oEvent) {
            console.log(oEvent.getSource());
            console.log(oEvent.getParameters());
            MessageToast.show("Press event fired!");
        },
        handleColumnDetailPress: function (oEvent) {
            console.log(oEvent.getSource());
            console.log(oEvent.getParameters());
            MessageToast.show("Detail press event fired!");
        },
        onAddNewTrackButtonPressed: function (oEvent) {
            window.location.href = "/artist/index.html";
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

    return AdminInterfaceController;
}, /* bExport= */ true);