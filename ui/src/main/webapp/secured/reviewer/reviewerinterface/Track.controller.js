sap.ui.define([
    'jquery.sap.global',
    'sap/ui/core/mvc/Controller',
    'sap/ui/core/Fragment',
    'sap/ui/model/Filter',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageToast",
    "sap/m/MessageBox",
    'sap/ui/model/SimpleType',
    'sap/ui/unified/ShellHeadItem'
], function (jQuery, Controller, Fragment, Filter, JSONModel, MessageToast, MessageBox, SimpleType, ShellHeadItem) {
    "use strict";

    var TrackController = Controller.extend("reviewerinterface.Track", {
        onInit: function () {
            sap.ui.getCore().AppContext.currentTrackView = this.getView();
            this.getView().setModel(sap.ui.getCore().AppContext.oTrackJSONModel, "mp3Info");
            this.getView().setModel(new JSONModel([
                {author : "author", comment : "LOOOL"},
                {author : "author2", comment : "MEGA LOOOOOOOOOOOOOOOOOL :)"}
            ]));
            console.log(this.getView().getModel("mp3Info").getJSON());
            sap.ui.getCore().AppContext.shellBackButton.setVisible(true);
            var wsURI = "ws://" + document.location.host + "/websocket/" + encodeURI(this.getView().getModel("mp3Info").getProperty("/title"));
            console.log(wsURI);
            /*this.socket = new WebSocket(wsURI);
            this.socket.onopen = function() {
                console.log("Websocket has been opened.");
            };
            this.socket.onmessage = function(oEvent) {
                console.log("Received WS message: " + oEvent.data);
            };*/
        },
        onExit: function () {
            this.socket.close();
        },
        onFeedInputPost: function (oEvent) {
            var sText = oEvent.getParameter("value");
            //console.log(sap.ui.getCore().byId("RootFolder.reviewerinterface.App"));
            console.log(sap.ui.getCore().AppContext.username);
            console.log(sText);
        }
    });

    return TrackController;
}, /* bExport= */ true);