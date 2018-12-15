package com.cirb.archiver.batch.utils;

import com.cirb.archiver.domain.JsonArchive;
import com.google.gson.Gson;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.transform.LineAggregator;

public class ArchiveJsonItemAggregator implements LineAggregator<JsonArchive>, StepExecutionListener {

  private Gson gson = new Gson();
  private boolean isFirstObject = true;

  @Override
  public String aggregate(final JsonArchive item) {
    if (isFirstObject) {
      isFirstObject = false;
      return "[" + gson.toJson(item);
    }
    return "," + gson.toJson(item);
  }

  public void setGson(final Gson gson) {
    this.gson = gson;
  }

  @Override
  public void beforeStep(final StepExecution stepExecution) {
    if (stepExecution.getExecutionContext().containsKey("isFirstObject")) {
      isFirstObject = Boolean.parseBoolean(stepExecution.getExecutionContext().getString("isFirstObject"));
    }
  }

  @Override
  public ExitStatus afterStep(final StepExecution stepExecution) {
    stepExecution.getExecutionContext().putString("isFirstObject", Boolean.toString(isFirstObject));
    return null;
  }

}
