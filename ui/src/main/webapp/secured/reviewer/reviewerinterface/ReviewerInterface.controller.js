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
            this.getView().setModel(new JSONModel(
                [
                    {title : "Test Title", artist : "TestArtist", email : "test@test.com", status : "UPLOADED"},
                    {title : "Test2", artist : "TestArtist2", email : "test2@test.com", status : "UPLOADED"}
                ]
            ));
            //this.refreshTable();
        },
        onExit: function () {
            //Pls do not leave
        },
        handleColumnPress: function (oEvent) {
            var aCells = oEvent.getSource().getCells();
            var oJSONModel = new JSONModel({title : aCells[0].getText(), artist : aCells[1].getText(), email : aCells[2].getText(), status : aCells[3].getText()});
            sap.ui.getCore().AppContext.oTrackJSONModel = oJSONModel;
            this.getOwnerComponent().getTargets().display("trackView");
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