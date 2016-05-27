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

    var AppContext = sap.ui.getCore().AppContext;
    var ReviewerInterfaceController = Controller.extend("reviewerinterface.ReviewerInterface", {
        onInit: function () {
            /*this.getView().setModel(new JSONModel(
                [
                    {title : "Test Title", artist : "TestArtist", email : "test@test.com", status : "UPLOADED"},
                    {title : "Test2", artist : "TestArtist2", email : "test2@test.com", status : "UPLOADED"}
                ]
            ));*/
            this.refreshTable();
        },
        onExit: function () {
            //Pls do not leave
        },
        handleColumnPress: function (oEvent) {
            var that = this;
            var aCells = oEvent.getSource().getCells();
            AppContext.oTrackJSONModel = new JSONModel({
                title: aCells[0].getText(),
                artist: aCells[1].getText(),
                email: aCells[2].getText(),
                status: aCells[3].getText()
            });
            $.ajax({
                url: "/rest/demo/mp3link/" + AppContext.oTrackJSONModel.getProperty("/title"),
                type: "GET",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    var oLinkJSON = new JSONModel(data);
                    console.log("Received JSON: " + oLinkJSON.getJSON());
                    AppContext.sMp3RelativeLink = oLinkJSON.getProperty("/link");
                    that.getOwnerComponent().getTargets().display("trackView");
                    if (AppContext.bTrackViewInitialized) {
                        AppContext.currentTrackController.onInit();
                    }
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
                },
                complete: function (xhr, status) {

                }
            });
        },
        refreshTable: function () {
            var oView = this.getView();
            $.ajax({
                url: "/rest/demo/findAll",
                type: "GET",
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    oView.setModel(new JSONModel(data));
                    console.log("Received JSON: " + oView.getModel().getJSON());
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
                },
                complete: function (xhr, status) {

                }
            });
        }
    });

    return ReviewerInterfaceController;
}, /* bExport= */ true);