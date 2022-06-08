package com.ge.urlshortener.repository;

import com.ge.urlshortener.domain.Url;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class UrlRepositoryTest {

    @InjectMocks
    private UrlRepository urlRepository;

    private String destination;

    private Url url;

    private Map<String, Url> store;

    @BeforeEach
    void setUp() {
        destination = "http://www.google.com";
        url = Url.of(destination);
        store = UrlRepository.getStore();
    }

    @AfterEach
    void after() {
        this.urlRepository.setSequence(0L);
    }

    @Test
    @DisplayName("db에 저장")
    void testWithSave() {
        // given

        // when
        Long actualId = this.urlRepository.save(url);

        // then
        Assertions.assertNotNull(actualId);
    }

    @Test
    @DisplayName("random string이 중복일 때 저장")
    void testWithDuplicatedRandomStringSave() {
        // given
        store.put(destination, url);

        // when
        Long actualId = this.urlRepository.save(url);
        Url storedUrl = this.urlRepository.getUrlById(actualId);

        // then
        Assertions.assertNotEquals(url.getShortenUrl(), storedUrl.getShortenUrl());
    }

    @Test
    @DisplayName("destination으로 검색")
    void testWithGetUrl() {
        // given
        store.put(destination, url);

        // when
        Url actual = this.urlRepository.findDestination(url.getShortenUrl());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(url.getDestination(), actual.getDestination());
        Assertions.assertEquals(url.getShortenUrl(), actual.getShortenUrl());
    }

    @Test
    @DisplayName("동시성 문제 테스트")
    public void testCounterWithConcurrency() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                this.urlRepository.save(url);
                latch.countDown();
            });
        }
        latch.await();
        long sequence = this.urlRepository.getSequence();
        Assertions.assertEquals(numberOfThreads, sequence);
    }
}
