package com.kumbarakala.app.data.repository;

import android.content.Context;
import com.kumbarakala.app.data.local.dao.ArtisanDao;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<ArtisanDao> artisanDaoProvider;

  public AuthRepository_Factory(Provider<Context> contextProvider,
      Provider<ArtisanDao> artisanDaoProvider) {
    this.contextProvider = contextProvider;
    this.artisanDaoProvider = artisanDaoProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(contextProvider.get(), artisanDaoProvider.get());
  }

  public static AuthRepository_Factory create(Provider<Context> contextProvider,
      Provider<ArtisanDao> artisanDaoProvider) {
    return new AuthRepository_Factory(contextProvider, artisanDaoProvider);
  }

  public static AuthRepository newInstance(Context context, ArtisanDao artisanDao) {
    return new AuthRepository(context, artisanDao);
  }
}
