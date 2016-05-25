sap.ui.define([
    "sap/ui/core/mvc/Controller"
], function (Controller) {
    "use strict";
    var AppController =  Controller.extend("reviewerinterface.App", {
        onInit : function () {
            var sUsername = getCookie("username");
            if (sUsername == "") sUsername = "anonymous";
            this.getView().byId("ShellHeadUserItem").setUsername(sUsername);
            sap.ui.getCore().AppContext = {};
            sap.ui.getCore().AppContext.oShellBackButton = this.getView().byId("shellBackButton");
            sap.ui.getCore().AppContext.sUsername = sUsername;
            sap.ui.getCore().AppContext.bTrackViewInitialized = false;
        },
        handleLogoffPress : function () {
            window.location.href = "/secured/logout";
        },
        handlePressHome : function () {
            window.location.href = "/";
        },
        handlePressBack : function (oEvent) {
            oEvent.getSource().setVisible(false);
            this.getOwnerComponent().getTargets().display("mainView");
        }
    });
    return AppController;
});
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}