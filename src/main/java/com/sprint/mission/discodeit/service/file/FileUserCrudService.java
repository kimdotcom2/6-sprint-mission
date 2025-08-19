package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.JCFUserService;

import java.io.*;
import java.util.UUID;

public class FileUserCrudService implements JCFUserService {
    @Override
    public void create(User user) {

        try(FileOutputStream fos = new FileOutputStream(user.getId().toString() + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);) {

            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean existById(UUID id) {

        try (FileInputStream fis = new FileInputStream(id.toString() + ".ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            User user = (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public void readById(UUID id) {

        try (FileInputStream fis = new FileInputStream(id.toString() + ".ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            User user = (User) ois.readObject();

            System.out.println(user.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readAll() {

    }

    @Override
    public void update(UUID id, String nickname, String email, String password, String description) {

    }

    @Override
    public void deleteById(UUID id) {

    }
}
