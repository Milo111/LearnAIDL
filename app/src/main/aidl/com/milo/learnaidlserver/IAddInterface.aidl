package com.milo.learnaidlserver;

import com.milo.learnaidlserver.Person;

interface IAddInterface {

    void setPerson(in Person person);

    List<Person> getPerson();
}
