package com.kumbarakala.app;

import com.kumbarakala.app.data.local.DatabaseSeeder;
import com.kumbarakala.app.data.repository.AuthRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class KumbaraKalaApp_MembersInjector implements MembersInjector<KumbaraKalaApp> {
  private final Provider<DatabaseSeeder> databaseSeederProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public KumbaraKalaApp_MembersInjector(Provider<DatabaseSeeder> databaseSeederProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.databaseSeederProvider = databaseSeederProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<KumbaraKalaApp> create(
      Provider<DatabaseSeeder> databaseSeederProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new KumbaraKalaApp_MembersInjector(databaseSeederProvider, authRepositoryProvider);
  }

  @Override
  public void injectMembers(KumbaraKalaApp instance) {
    injectDatabaseSeeder(instance, databaseSeederProvider.get());
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.kumbarakala.app.KumbaraKalaApp.databaseSeeder")
  public static void injectDatabaseSeeder(KumbaraKalaApp instance, DatabaseSeeder databaseSeeder) {
    instance.databaseSeeder = databaseSeeder;
  }

  @InjectedFieldSignature("com.kumbarakala.app.KumbaraKalaApp.authRepository")
  public static void injectAuthRepository(KumbaraKalaApp instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
