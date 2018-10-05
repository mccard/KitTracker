package edu.uco.mcamposcardoso.kittracker.interfaces;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.uco.mcamposcardoso.kittracker.types.ScanInformation;

/**
 * Created by matheuscamposcardoso on 24/10/17.
 */
public interface AlunoConfirmationListener {
    void onAlunoConfirmation(String matricula);
}
