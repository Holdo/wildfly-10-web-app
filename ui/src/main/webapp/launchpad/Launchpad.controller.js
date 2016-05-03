sap.ui.define([
    'jquery.sap.global',
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/json/JSONModel'
], function(jQuery, Controller, JSONModel) {
    "use strict";

    var LaunchpadController = Controller.extend("launchpad.Launchpad", {
        onInit: function(oEvent) {
            //Set JSON model
            var tileCollectionJSON= {
                "TileCollection" : [
                    {
                        "icon" : "sap-icon://microphone",
                        "type" : "Monitor",
                        "title" : "Artist"
                    },
                    {
                        "icon" : "sap-icon://complete",
                        "type" : "Monitor",
                        "title" : "Reviewer"
                    },
                    {
                        "icon" : "sap-icon://sys-monitor",
                        "type" : "Monitor",
                        "title" : "Admin"
                    }
                ]
            };
            this.tileModelJSON = new JSONModel();
            this.tileModelJSON.setData({data: tileCollectionJSON});
            this.getView().setModel(this.tileModelJSON);
        },
        onExit : function (oEvent) {

        },
        handleTilePress : function (oEvent) {
            switch(oEvent.oSource.getTitle()) {
                case "Artist":
                    window.location.href = "/artist/index.html";
                    break;
                case "Reviewer":
                    window.location.href = "/secured/reviewer/index.html";
                    break;
                case "Admin":
                    window.location.href = "/secured/admin/index.html";
                    break;
                default:
                    break;
            }
        }
    });

    return LaunchpadController;
})
;