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
            sap.ui.getCore().AppContext.shellBackButton = this.getView().byId("shellBackButton");
            sap.ui.getCore().AppContext.username = sUsername;
        },
        handleLogoffPress : function () {
            window.location.href = "/secured/logout";
        },
        handlePressHome : function () {
            window.location.href = "/";
        },
        handlePressBack : function (oEvent) {
            this.getOwnerComponent().getTargets().display("mainView");
            oEvent.getSource().setVisible(false);
            console.log(sap.ui.getCore().AppContext.currentTrackView);
            sap.ui.getCore().AppContext.currentTrackView.destroy();
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