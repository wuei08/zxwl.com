package com.zxwl.web.generator;

import com.zxwl.web.generator.template.CodeTemplate;

import java.util.List;

/**
 * @author zhouhao
 */
public interface Generator<IN> {
     void start(CodeTemplate<IN> codeTemplate,CodeTemplate<IN>... codeTemplates);
}
