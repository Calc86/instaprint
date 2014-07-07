package com.printer;

import java.io.IOException;

/**
 * Created by calc on 04.07.14.
 *
 */
public class IrfanViewPrinter implements Printer {
    private final String IrfanViewPath;

    public IrfanViewPrinter(String IrfanViewPath) {
        this.IrfanViewPath = IrfanViewPath;
    }

    @Override
    public void print(String file) {
        Runtime r = Runtime.getRuntime();
        Process p;
        try {
            String command = this.IrfanViewPath + " " + file + " /print";
            System.out.println(command);
            p = r.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
