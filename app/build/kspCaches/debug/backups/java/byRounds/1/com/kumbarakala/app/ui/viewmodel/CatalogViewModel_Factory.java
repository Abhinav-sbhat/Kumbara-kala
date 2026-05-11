package com.kumbarakala.app.ui.viewmodel;

import com.kumbarakala.app.data.repository.AuthRepository;
import com.kumbarakala.app.data.repository.ProductRepository;
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
public final class CatalogViewModel_Factory implements Factory<CatalogViewModel> {
  private final Provider<ProductRepository> productRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public CatalogViewModel_Factory(Provider<ProductRepository> productRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.productRepositoryProvider = productRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public CatalogViewModel get() {
    return newInstance(productRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static CatalogViewModel_Factory create(
      Provider<ProductRepository> productRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new CatalogViewModel_Factory(productRepositoryProvider, authRepositoryProvider);
  }

  public static CatalogViewModel newInstance(ProductRepository productRepository,
      AuthRepository authRepository) {
    return new CatalogViewModel(productRepository, authRepository);
  }
}
