sap.ui.define( ["sap/ui/core/UIComponent"], function (UIComponent) {
    "use strict";
    return UIComponent.extend("RootFolder.reviewerinterface.Component", {
        metadata: {
            rootView: "RootFolder.reviewerinterface.App",
            routing: {
                config: {
                    targetsClass: "sap.m.routing.Targets",
                    viewPath: "RootFolder.reviewerinterface",
                    controlId: "rootControl",
                    controlAggregation: "pages",
                    viewType: "XML"
                },
                targets: {
                    page1: {
                        viewName: "ReviewerInterface",
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