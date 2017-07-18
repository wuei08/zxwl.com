package com.zxwl.platform.generator;

import com.zxwl.platform.generator.template.CodeTemplate;

import java.util.List;

/**
 * @author zhouhao
 */
public interface Generator<IN> {
     void start(CodeTemplate<IN> codeTemplate,CodeTemplate<IN>... codeTemplates);
}
