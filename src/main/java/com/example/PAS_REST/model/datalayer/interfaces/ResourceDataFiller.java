package com.example.PAS_REST.model.datalayer.interfaces;

import com.example.PAS_REST.model.datalayer.obj.Resources.AudioBook;
import com.example.PAS_REST.model.datalayer.obj.Resources.Book;

import java.util.List;

public interface ResourceDataFiller {
    void fill(List<Book> books, List<AudioBook> audioBooks);
}
