package com.zxwl.web.generator.template;

import java.util.Map;

public interface CodeTemplate<IN> {

    Map<String, Object> getVars();

    TemplateInput<IN> getInput();

    TemplateOutput getOutPut();
}
