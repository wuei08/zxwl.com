package com.zxwl.platform.generator.writer;

import com.zxwl.platform.generator.SourceCode;

/**
 * @author zhouhao
 */
public interface SourceWriter {
    void write(SourceCode code)throws Exception;
}
