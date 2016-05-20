sap.ui.define( ["sap/ui/core/UIComponent"], function (UIComponent) {
    "use strict";
    return UIComponent.extend("RootFolder.admininterface.Component", {
        metadata: {
            rootView: "RootFolder.admininterface.App",
            routing: {
                config: {
                    targetsClass: "sap.m.routing.Targets",
                    viewPath: "RootFolder.admininterface",
                    controlId: "rootControl",
                    controlAggregation: "pages",
                    viewType: "XML"
                },
                targets: {
                    page1: {
                        viewName: "AdminInterface",
                        viewLevel: 0
                    }
                }
            }
        },
        init : function () {
            UIComponent.prototype.init.apply(this, arguments);
            this.getTargets().display("page1");
        }
    });
}, /* bExport= */ true);