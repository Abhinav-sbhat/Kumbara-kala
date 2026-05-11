package com.kumbarakala.app.data.local;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseSeeder_Factory implements Factory<DatabaseSeeder> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> databaseProvider;

  public DatabaseSeeder_Factory(Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider) {
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DatabaseSeeder get() {
    return newInstance(contextProvider.get(), databaseProvider.get());
  }

  public static DatabaseSeeder_Factory create(Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseSeeder_Factory(contextProvider, databaseProvider);
  }

  public static DatabaseSeeder newInstance(Context context, AppDatabase database) {
    return new DatabaseSeeder(context, database);
  }
}
