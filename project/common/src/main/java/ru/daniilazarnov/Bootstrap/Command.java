package ru.daniilazarnov.Bootstrap;

public enum Command {
    HELP("Вывод этой помощи"),
    SEND_FILE("Отправить файл"),
    GET_FILE("Скачать файл"),
    RENAME_FILE("Переименовать файл"),
    REMOVE_FILE("Удалить файл"),
    SET_ATTRIBUTES_FOR_FILE("Установить аттрибуты файла (SRV - хранить только на сервере, LOC - хранить только локально, ALL - и там и там)"),
    SAFF("Алиас для SET_ATTRIBUTES_FOR_FILE"),
    GET_ATTRIBUTES_FOR_FILE("Проверить аттрибуты файла (SRV - хранить только на сервере, LOC - хранить только локально, ALL - и там и там)"),
    GAFF("Алиас для GET_ATTRIBUTES_FOR_FILE"),
    GET_DIR("Локальная директория хранилища"),
    SET_DIR("Поменять локальную директорию хранилища"),
    CHANGE_PASS("Поменять пароль");

    private String title;

    Command(String title) {
        this.title = title;
    }

    public static void getHelp() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        for (Command cmd : Command.values()) {
            System.out.println(ANSI_RED + cmd.name() + ANSI_RESET + cmd.toString());
        }
    }

    @Override
    public String toString() {
        return ":\n- " + title + "";
    }
}
