package com.devansh.anchor.parser;

import org.springframework.stereotype.Component;

@Component
public class ParserFactory {

    private final DefaultCodeParser defaultCodeParser;

    public ParserFactory(DefaultCodeParser defaultCodeParser) {
        this.defaultCodeParser = defaultCodeParser;
    }

    public CodeParser getParser(String fileName) {

        return defaultCodeParser;
    }

}