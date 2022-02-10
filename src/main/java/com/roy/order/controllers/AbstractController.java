package com.roy.order.controllers;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
@Profile("!test")
@NoArgsConstructor
public abstract class AbstractController implements CommandLineRunner {

    public Scanner scanner = new Scanner(System.in);

}
