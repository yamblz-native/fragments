package ru.yandex.yamblz.domain.repository;

import com.petertackage.assertrx.Assertions;

import org.assertj.core.api.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.yandex.yamblz.domain.datasource.IDataSource;
import ru.yandex.yamblz.domain.datasource.MockDataSource;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;
import ru.yandex.yamblz.domain.repository.exception.NotFoundException;
import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static com.petertackage.assertrx.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Created by Александр on 06.08.2016.
 */
public class BardRepositoryTest {

    private MockDataSource mockDataSource = new MockDataSource();

    private IBardRepository bardRepository;

    @Before
    public void setUp() throws Exception {
        mockDataSource = new MockDataSource();
        this.bardRepository = new BardRepository(mockDataSource);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void fetch_bard_by_id(){
        TestSubscriber<Bard> ts = new TestSubscriber<>();
        bardRepository.getBardById(1).subscribe(ts);

        assertThat(ts)
                .afterTerminalEvent()
                .hasNoErrors()
                .hasValueCount(1)
                .hasReceivedValue(mockDataSource.secondBard);
    }

    @Test
    public void fetch_bard_by_unknown_id(){
        TestSubscriber<Bard>  ts = new TestSubscriber<>();
        bardRepository.getBardById(999).subscribe(ts);

        assertThat(ts)
                .afterTerminalEvent()
                .hasNoValues()
                .hasError(NotFoundException.class);
    }

    @Test
    public void fetch_all_bard_by_genre_id(){
        TestSubscriber<List<Bard>> ts = new TestSubscriber<List<Bard>>();
        bardRepository.getAllBardByGenre(0).subscribe(ts);

        assertThat(ts)
                .afterTerminalEvent()
                .hasValueCount(1)
                .hasReceivedValue(Arrays.asList(mockDataSource.firstBard, mockDataSource.secondBard))
                .hasNoErrors();
    }



}