# Archiving and encryption application with spring boot

## Main classes

### ArchivingBatch : 

Configuring the `postgresToCsv` tasklet which will read all consumer and provider records from the database, and will use the `itemWriter` (see below) to write those records to the csv file located in the `outputDirectory` which can be specified in the `application.yml` file (because it uses the placeholder `${"batch.output-directory"})`.

```
@Bean
@StepScope
protected Tasklet postgresToCsv() {
    return new 
    PostgresToCsv(consumerRepository,providerRepository,writer());
}
```

In the ItemWriter bean, we chose to use the FlatFileItemWriter because its the one that gives us the ability to write to a file.

```
@Bean
@StepScope
public FlatFileItemWriter<Archive> writer() {
    FlatFileItemWriter<Archive> writer = new FlatFileItemWriter<>();
    writer.setResource(new FileSystemResource(outputDirectory));
    ...
}
```

The following line tells the writer where to put the file that will be created.

```
writer.setResource(new FileSystemResource(outputDirectory));
```


This one specifies the csv column names as a String separated by the csv delimiter
```
writer.setHeaderCallback(new FlatFileHeaderCallback() {
    ...
}
```


And this one tells the writer which fields to choose from the Archive object that we passed to the writer.
```
writer.setLineAggregator(new DelimitedLineAggregator<Archive>() {
    ...
}
```


The spring batch Batch is a collection of one or multiple jobs, each job can contain one to many steps.
Here we configure the once step we have for the moment which runs the tasklet we discussed above. We'll have other steps in the future (a step for archiving the files ..).

```
@Bean
protected Step postgresToCsvStep() {
    return 
    stepBuilderFactory.get("postgresToCsvStep".tasklet(postgresToCsv()).build();
}
```

And here we have our job build using the one step we have for now.

```
@Bean
public Job administrationJob() {
    return 
    jobBuilderFactory.get("archivingJob").incrementer(new RunIdIncrementer()).start(postgresToCsvStep())
            .build();
}
```


### PostgresToCsv :

This class contains one method: the one that loads all consumer and provider records from the database and writes them to the csv file.

```
@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    List<Consumer> consumers = consumerRepository.findAll();
    List<Provider> providers = providerRepository.findAll();
    List<Archive> archives = new ArrayList<>();
    for(Consumer consumer : consumers) {
        for(Provider provider : providers) {
            // consumer and provider of the same transaction
            if(consumer.getTransactionId() != null && consumer.getTransactionId().equals(provider.getTransactionId())) {
                Archive archive = new Archive(new Date(), consumer, provider);
                archives.add(archive);
            }
        }
    }
    
    this.itemWriter.write(archives);
    return RepeatStatus.FINISHED;
}
```

we loop through each of them to find consumer and provider of the same transaction to align them in the same ligne of the csv file .


### ArchiverBatchApplication :

This class is the one responsible for running the application.

It contains the `lunchMigration()` method which is annotated with `@Scheduled(cron = "${batch.archivingCron}")`. This annotation is the one that tell spring batch when to run the method. And since it has the placeholder `"${batch.archivingCron}"`, this means that it reads the values from the application.yml

the  `"${batch.archivingCron}"` in the application.yml contains the value : 0 * * * * * which according to the syntax `second, minute, hour, day of month, month, day(s) of week` means that I want the batch to run every minute.

More info about `@Scheduled(cron = xx)` syntax can be found [here](https://stackoverflow.com/questions/26147044/spring-cron-expression-for-every-day-101am) 


## Running the application

To run the application, you just have to specify in the application.yml the address for you database as well as its name. You can do that by editing the `db:x` where x is {host, port, name, username, password }
