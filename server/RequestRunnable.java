package server;

import java.io.EOFException;
import java.io.IOException;

import shared.Request;

public interface RequestRunnable
{
    public void run(Request request) throws EOFException, IOException, ClassNotFoundException;
}