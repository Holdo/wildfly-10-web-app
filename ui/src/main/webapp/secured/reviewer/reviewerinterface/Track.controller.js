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

    var sLastTrackTitle = null;
    var AppContext = sap.ui.getCore().AppContext;

    var TrackController = Controller.extend("reviewerinterface.Track", {
        onInit: function () {
            var sRequestedTrackTitle = AppContext.oTrackJSONModel.getProperty("/title");
            if (sLastTrackTitle === null || sLastTrackTitle != sRequestedTrackTitle) {
                if (sLastTrackTitle != sRequestedTrackTitle) {
                    if (this.socket != undefined && this.socket.readyState != 3) this.socket.close();
                }
                sLastTrackTitle = sRequestedTrackTitle;
                AppContext.currentTrackController = this;
                this.getView().setModel(AppContext.oTrackJSONModel, "mp3Info");
                /*this.getView().setModel(new JSONModel([
                    {author: "author", comment: "LOOOL"},
                    {author: "author2", comment: "MEGA LOOOOOOOOOOOOOOOOOL :)"}
                ]));*/
                this.getView().setModel(new JSONModel());
                var oFeedJSONModel = this.getView().getModel();
                var sFeedJSON = "[]";
                var wsURI = "ws://" + document.location.host + "/websocket/" + encodeURI(this.getView().getModel("mp3Info").getProperty("/title"));
                console.log(wsURI);
                this.socket = new WebSocket(wsURI);
                this.socket.onopen = function () {
                    console.log("Websocket has been opened.");
                };
                this.socket.onmessage = function (oEvent) {
                    var sReceivedJSON = oEvent.data;
                    console.log("Received message from WS: " + sReceivedJSON);
                    if (sFeedJSON != "[]") sReceivedJSON = sReceivedJSON + ", ";
                    sFeedJSON = sFeedJSON.substring(1, sFeedJSON.length);
                    sFeedJSON = "[" + sReceivedJSON + sFeedJSON;
                    oFeedJSONModel.setJSON(sFeedJSON);
                };
            }
            AppContext.oShellBackButton.setVisible(true);
            AppContext.bTrackViewInitialized = true;
        },
        onExit: function () {
            if (this.socket != undefined && this.socket.readyState != 3) this.socket.close();
        },
        onFeedInputPost: function (oEvent) {
            var oJSON = {
                title : sLastTrackTitle,
                author : AppContext.sUsername,
                comment : oEvent.getParameter("value")
            };
            var sJSON = JSON.stringify(oJSON);
            console.log("Sending to WS: " + sJSON);
            this.socket.send(sJSON);
        }
    });

    return TrackController;
}, /* bExport= */ true);