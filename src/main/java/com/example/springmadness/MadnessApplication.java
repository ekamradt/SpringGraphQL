package com.example.springmadness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MadnessApplication {

    public static void main(String[] args) {
        SpringApplication.run(MadnessApplication.class, args);
    }

//    @PostConstruct
//    public void setup() {
//        // To parse a value into LocalDate from an input String.  And other date types for that matter.
//        csvMapper.registerModule(new JavaTimeModule());
//    }
//
//    // Show report upon startup of the application
//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        readFileIntoDatabase("CellPhone.csv", CellPhone.class, cellPhoneRepo);
//        readFileIntoDatabase("CellPhoneUsageByMonth.csv", CellUsageMonth.class, cellUsageMonthRepo);
//
//        final WcfReport report = reportRepo.getReport();
//        report.showReport();
//    }
//
//    // Read a sfile and write a list of Entities to the database.
//    private <T> void readFileIntoDatabase(String csvFilename, Class<T> entityClass, CrudRepository<T, ?> repo) {
//        try {
//            final List<T> entities = readWholeFileIntoList(csvFilename, entityClass);
//            final Iterable<T> s = repo.saveAll(entities);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    // Read the input file into a list of Entities.
//    private <T> List<T> readWholeFileIntoList(String csvFilename, Class<T> entityClass) throws IOException {
//        final List<T> entities = new ArrayList<>();
//        final CsvSchema schema = CsvSchema.emptySchema().withHeader();
//        final ObjectReader oReader = csvMapper.reader(entityClass).with(schema);
//        try (Reader reader = new FileReader(CSV_FILE_PATH_PREFIX + csvFilename)) {
//            final MappingIterator<T> objectMappingIterator = oReader.readValues(reader);
//            while (objectMappingIterator.hasNext()) {
//                final T next = objectMappingIterator.next();
//                entities.add(next);
//            }
//        }
//        return entities;
//    }
}
