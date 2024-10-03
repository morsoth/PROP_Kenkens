package edu.upc.prop.clusterxx.persistence;

import edu.upc.prop.clusterxx.domain.Kenken;
import edu.upc.prop.clusterxx.exceptions.CantCreateKenkenException;
import edu.upc.prop.clusterxx.exceptions.KenkenNotFoundException;
import edu.upc.prop.clusterxx.exceptions.UserNotFoundException;
import edu.upc.prop.clusterxx.exceptions.CantCreateUserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Controlador para la persistencia de la aplicación.
 * @author Raúl Gilabert Gámez
 */
public class PersistenceController {

    /**
     * Crea los directorios necesarios para la aplicación.
     */
    public PersistenceController() {
        File data = new File("data");
        data.mkdirs();
        File kenkens = new File("data/kenkens");
        kenkens.mkdirs();
        File saves = new File("data/kenkens/saves");
        saves.mkdirs();
        File users = new File("data/users");
        users.mkdirs();
    }

    /**
     * Guarda un Kenken en un archivo.
     * @param kenken El Kenken a guardar.
     * @param filename El nombre del archivo en el que se guardará el Kenken.
     * @throws CantCreateKenkenException Si no se puede crear el archivo.
     */
    public void saveKenken(String[] kenken, String filename) throws CantCreateKenkenException {
        try {
            // crear archivo
            String path = "data/kenkens/" + filename + ".ken";
            File file = new File(path);
            file.createNewFile();
            // escribir datos en el archivo
            FileWriter writer = new FileWriter(file);
            for (String line : kenken) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new CantCreateKenkenException();
        }
    }

    /**
     * Carga un Kenken desde un archivo.
     * @param filename El nombre del archivo desde el cual cargar el Kenken.
     * @return El Kenken cargado desde el archivo.
     * @throws KenkenNotFoundException Si el archivo no existe.
     */
    public String[] loadKenken(String filename) throws KenkenNotFoundException {
        String path = "data/kenkens/" + filename + ".ken";
        String[] kenken = new String[0];

        try {
            // obtener datos del archivo
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String[] newKenken = new String[kenken.length + 1];
                System.arraycopy(kenken, 0, newKenken, 0, kenken.length);
                newKenken[kenken.length] = scanner.nextLine();
                kenken = newKenken;
            }
        } catch (FileNotFoundException e) {
            throw new KenkenNotFoundException();
        }
        return kenken;
    }

    /**
     * Guarda los datos de un usuario en un archivo.
     * @param user El nombre del usuario.
     * @param password La contraseña del usuario.
     * @param points Los puntos del usuario.
     * @throws CantCreateUserException Si no se puede crear el archivo.
     */
    public void saveUserData(String user, String password, int points) throws CantCreateUserException {
        String path = "data/users/" + user + ".usr";

        try {
            // crear archivo
            File file = new File(path);
            file.createNewFile();
            // escribir datos en el archivo
            FileWriter writer = new FileWriter(file);
            writer.write(user + "\n");
            writer.write(password + "\n");
            writer.write(points + "\n");
            writer.close();
        } catch (IOException e) {
            throw new CantCreateUserException(user);
        }
    }

    /**
     * Carga los datos de un usuario desde un archivo.
     * @param name El nombre del usuario.
     * @return Los datos del usuario.
     *         El primer elemento es el nombre del usuario.
     *         El segundo elemento es la contraseña del usuario.
     *         El tercer elemento son los puntos del usuario.
     * @throws UserNotFoundException Si el archivo no existe.
     */
    public String[] loadUserData(String name) throws UserNotFoundException {
        String path = "data/users/" + name + ".usr";
        String[] data = new String[3];

        try {
            // obtener datos del archivo
            Scanner scanner = new Scanner(new File(path));
            data[0] = scanner.nextLine();
            data[1] = scanner.nextLine();
            data[2] = scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new UserNotFoundException(name);
        }
        return data;
    }

    /**
     * Comprueba si un usuario existe.
     * @param name El nombre del usuario.
     * @return True si el usuario existe, false en caso contrario.
     */
    public boolean userExists(String name) {
        String path = "data/users/" + name + ".usr";
        File file = new File(path);
        return file.exists();
    }

    /**
     * Obtiene los nombres de los usuarios.
     * @return Los nombres de los usuarios.
     */
    public String[] getUsers() {
        File users = new File("data/users");
        String[] files = users.list();
        for (int i = 0; i < files.length; ++i) {
            files[i] = files[i].substring(0, files[i].length() - 4);
        }
        return files;
    }

    /**
     * Obtiene los nombres de los Kenkens que no son partida guardada.
     * @return Los nombres de los Kenkens que no son partida guardada.
     */
    public String[] getKenkens() {
        File kenkens = new File("data/kenkens");
        String[] files = kenkens.list();

        int counter_files_no_user = 0;

        for (int i = 0; i < files.length; ++i) {
            // si el final del fichero es .ken se añade a la lista
            if (files[i].substring(files[i].length() - 4).equals(".ken")) {
                files[i] = files[i].substring(0, files[i].length() - 4);
                counter_files_no_user++;
            } else {
                files[i] = null;
            }
        }

        String to_return[] = new String[counter_files_no_user];

        int counter = 0;

        for (int i = 0; i < files.length; ++i) {
            if (files[i] != null) {
                to_return[counter] = files[i];
                counter++;
            }
        }

        return to_return;
    }

    /**
     * Obtiene los nombres de los Kenkens de un usuario.
     * @param user El nombre del usuario.
     * @return el nombre del fichero con la partida guardada si existe o null si no hay nada.
     */
    public String getKenkenUser(String user) {
        File kenkens = new File("data/kenkens/saves");
        String[] files = kenkens.list();

        // Se busca un fichero con el nombre del usuario
        for (int i = 0; i < files.length; ++i) {
            if (files[i].length() >= user.length() + 4 && files[i].substring(0, user.length()).equals(user)) {
                return files[i].substring(0, files[i].length() - 4);
            }
        }

        return null;
    }

    /**
     * Importa un Kenken desde un archivo.
     * @param path La ruta del archivo.
     * @param filename El nombre del archivo.
     * @throws CantCreateKenkenException Si no se puede crear el archivo.
     */
    public void importKenken(String path, String filename) throws CantCreateKenkenException {
        try {
            // crear archivo
            File file = new File("data/kenkens/" + filename + ".ken");
            file.createNewFile();
            // escribir datos en el archivo
            FileWriter writer = new FileWriter(file);
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                writer.write(scanner.nextLine() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new CantCreateKenkenException();
        }
    }
}
