package com.kumbarakala.app.di;

import com.kumbarakala.app.data.local.AppDatabase;
import com.kumbarakala.app.data.local.dao.StoryCardDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DatabaseModule_ProvideStoryCardDaoFactory implements Factory<StoryCardDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideStoryCardDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StoryCardDao get() {
    return provideStoryCardDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStoryCardDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStoryCardDaoFactory(databaseProvider);
  }

  public static StoryCardDao provideStoryCardDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStoryCardDao(database));
  }
}
