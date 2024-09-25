**Руководство по использованию системы погрузки грузов**

**Входные данные**

- Путь к файлу груза: Путь к файлу, содержащему информацию о грузе.
- Путь к файлу транспорта: Путь к файлу, содержащему информацию о транспортных средствах.
- Количество транспортных средств: Количество транспортных средств, которые необходимо создать.
- Алгоритм загрузки груза: Алгоритм, используемый для загрузки груза в транспортные средства (например, EL для
  равномерной
  загрузки или MES для максимальной эффективности).

**Выходные данные:**

- Загруженные транспортные средства: Список транспортных средств с соответствующим грузом и их количеством.
- Сохраненные данные: Программа сохраняет транспортные средства и их груз в файле в формате JSON,
  после чего выводит их в консоль для графического отображения

**Пример использования программы:**

Вводится путь к файлу с грузами.

- Программа считывает файл и выводит ошибки, если он есть

Вводится количество транспортных средств для создания их в системе.

- Программа создает необходимое количество транспортных средств

Вводится путь к файлу с транспортными средствами.

- Выполняется чтение и выводится информация о полученных данных, транспортные средства так же будут сохранены в системе
  для дальнейшей погрузки

Выбирается алгоритм загрузки груза.

- Вы выбираете алгоритм и, исходя из этого, выполняется погрузка

Вводится путь к файлу для сохранения.

- Программа сохраняет JSON файл с данными в указаный путь, после чего выводит список транспортных средств с погруженными
  в них посылками

**Программа имеет следующие ограничения:**

- Формат файла груза: Текстовый, не более 10000 посылок.
- Формат файла транспорта: JSON, не более 10000 транспортных средств.
- Количество транспортных средств: Количество транспортных средств не должно превышать 10000.