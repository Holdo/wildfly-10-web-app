sap.ui.define( ["sap/ui/core/UIComponent"], function (UIComponent) {
    "use strict";
    return UIComponent.extend("RootFolder.audioinput.Component", {
        metadata: {
            rootView: "RootFolder.audioinput.App",
            routing: {
                config: {
                    targetsClass: "sap.m.routing.Targets",
                    viewPath: "RootFolder.audioinput",
                    controlId: "rootControl",
                    controlAggregation: "pages",
                    viewType: "XML"
                },
                targets: {
                    page1: {
                        viewName: "AudioInput",
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