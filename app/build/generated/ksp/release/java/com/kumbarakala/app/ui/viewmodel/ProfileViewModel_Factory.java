package com.kumbarakala.app.ui.viewmodel;

import com.kumbarakala.app.data.repository.ArtisanRepository;
import com.kumbarakala.app.data.repository.AuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<ArtisanRepository> artisanRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ProfileViewModel_Factory(Provider<ArtisanRepository> artisanRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.artisanRepositoryProvider = artisanRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(artisanRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<ArtisanRepository> artisanRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ProfileViewModel_Factory(artisanRepositoryProvider, authRepositoryProvider);
  }

  public static ProfileViewModel newInstance(ArtisanRepository artisanRepository,
      AuthRepository authRepository) {
    return new ProfileViewModel(artisanRepository, authRepository);
  }
}
