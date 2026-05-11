package com.kumbarakala.app.di;

import com.kumbarakala.app.data.local.AppDatabase;
import com.kumbarakala.app.data.local.dao.ArtisanDao;
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
public final class DatabaseModule_ProvideArtisanDaoFactory implements Factory<ArtisanDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideArtisanDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ArtisanDao get() {
    return provideArtisanDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideArtisanDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideArtisanDaoFactory(databaseProvider);
  }

  public static ArtisanDao provideArtisanDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideArtisanDao(database));
  }
}
