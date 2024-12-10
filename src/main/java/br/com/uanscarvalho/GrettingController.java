package br.com.uanscarvalho;

import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GrettingController {

    private static final String template = "Ola, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/gretting")
    public Gretting gretting(@RequestParam(value = "name", defaultValue = "Mundo") String name) {

        return new Gretting(counter.incrementAndGet(), String.format(template, name));
    }


}
