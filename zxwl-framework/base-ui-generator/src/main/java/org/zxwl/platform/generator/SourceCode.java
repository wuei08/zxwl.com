package com.zxwl.web.generator;

import com.zxwl.web.generator.writer.AppendSourceWriter;
import com.zxwl.web.generator.writer.DefaultSourceWriter;
import com.zxwl.web.generator.writer.SourceWriter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public interface SourceCode {
    String getFileName();

    InputStream readCode();

    ReplaceMod getReplaceMode();

    default void write() throws Exception {
        if (null == getReplaceMode()) throw new NullPointerException("请设置替换模式");
        getReplaceMode().writer.write(this);
    }

    enum ReplaceMod {
        all(new DefaultSourceWriter(false)),
        append(new AppendSourceWriter()),
        ignore(new DefaultSourceWriter(true));
        private final SourceWriter writer;

        ReplaceMod(SourceWriter writer) {
            this.writer = writer;
        }
    }

    static void main(String[] args) throws Exception {
        SourceCode code = new SourceCode() {
            @Override
            public String getFileName() {
                return "/home/zhouhao/IdeaProjects/zxwl-platform/zxwl-platform-generator/target/Test.java";
            }

            @Override
            public InputStream readCode() {
                return new ByteArrayInputStream("test sd".getBytes());
            }

            @Override
            public ReplaceMod getReplaceMode() {
                return ReplaceMod.all;
            }
        };
        code.write();
    }

}
