package com.zxwl.web.generator.writer;

import com.zxwl.web.generator.SourceCode;

/**
 * @author zhouhao
 */
public interface SourceWriter {
    void write(SourceCode code)throws Exception;
}
